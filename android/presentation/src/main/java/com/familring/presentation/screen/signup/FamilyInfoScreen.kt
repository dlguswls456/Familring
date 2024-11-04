package com.familring.presentation.screen.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.familring.presentation.R
import com.familring.presentation.component.CustomDropdownMenu
import com.familring.presentation.component.CustomDropdownMenuStyles
import com.familring.presentation.component.RoundLongButton
import com.familring.presentation.component.TopAppBar
import com.familring.presentation.theme.Black
import com.familring.presentation.theme.Gray01
import com.familring.presentation.theme.Typography
import com.familring.presentation.theme.White
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FamilyInfoRoute(
    modifier: Modifier,
    viewModel: SignUpViewModel,
    showSnackBar: (String) -> Unit,
    popUpBackStack: () -> Unit,
    navigateToDone: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is SignUpUiEvent.Success -> {
                    if (uiState.value.make) {
                        navigateToDone()
                    } else {
                        Log.d("nakyung", "state: ${uiState.value}")
                        coroutineScope {
                        }
                        viewModel.joinFamily(uiState.value.familyCode)
                    }
                }

                is SignUpUiEvent.Error -> {
                    showSnackBar(event.message)
                }

                is SignUpUiEvent.JoinSuccess -> {
                    navigateToHome()
                }

                else -> {}
            }
        }
    }

    FamilyInfoScreen(
        modifier = modifier,
        popUpBackStack = popUpBackStack,
        updateRole = viewModel::updateRole,
        join = viewModel::join,
    )
}

@Composable
fun FamilyInfoScreen(
    modifier: Modifier = Modifier,
    popUpBackStack: () -> Unit = {},
    updateRole: (String) -> Unit = {},
    join: () -> Unit = {},
) {
    var role by remember { mutableStateOf("M") }

    LaunchedEffect(role) {
        updateRole(role)
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = White,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "가족 구성원 정보 입력",
                        color = Black,
                        style = Typography.headlineMedium.copy(fontSize = 22.sp),
                    )
                },
                onNavigationClick = popUpBackStack,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                text = "우리 가족의 정보를 알려 주세요!",
                style = Typography.bodyLarge,
                color = Black,
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.07f))
            Image(
                modifier =
                Modifier
                    .fillMaxWidth(0.45f)
                    .aspectRatio(1f),
                painter = painterResource(id = R.drawable.img_family),
                contentDescription = "img_family",
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Text(
                text = "나는 우리 가족의",
                style = Typography.headlineLarge,
                color = Gray01,
            )
            Spacer(modifier = Modifier.height(15.dp))
            CustomDropdownMenu(
                menuItems =
                    listOf(
                        "엄마" to { role = "M" },
                        "아빠" to { role = "F" },
                        "딸" to { role = "D" },
                        "아들" to { role = "S" },
                    ),
                styles = CustomDropdownMenuStyles(),
                cornerRadius = 10,
                iconDrawable = R.drawable.ic_arrow_down,
                expandedIconDrawable = R.drawable.ic_arrow_up,
                menuItemHeight = 45,
                textStyle = Typography.titleMedium.copy(fontSize = 24.sp, color = Black),
                menuItemTextStyle =
                    Typography.headlineMedium.copy(
                        fontSize = 20.sp,
                        color = Black,
                    ),
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
            RoundLongButton(
                onClick = join,
                text = "가입 완료",
            )
        }
    }
}

@Preview
@Composable
fun FamilyCountScreenPreview() {
    FamilyInfoScreen()
}
