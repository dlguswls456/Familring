package com.familring.presentation.screen.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.familring.presentation.component.TopAppBar
import com.familring.presentation.component.button.RoundLongButton
import com.familring.presentation.component.textfield.CustomTextField
import com.familring.presentation.theme.Black
import com.familring.presentation.theme.Gray01
import com.familring.presentation.theme.Gray02
import com.familring.presentation.theme.Typography
import com.familring.presentation.theme.White

@Composable
fun NicknameRoute(
    modifier: Modifier,
    viewModel: SignUpViewModel,
    popUpBackStack: () -> Unit,
    navigateToPicture: () -> Unit,
) {
    NicknameScreen(
        modifier = modifier,
        popUpBackStack = popUpBackStack,
        navigateToPicture = navigateToPicture,
        updateNickname = viewModel::updateNickname,
    )
}

@Composable
fun NicknameScreen(
    modifier: Modifier = Modifier,
    popUpBackStack: () -> Unit = {},
    navigateToPicture: () -> Unit = {},
    updateNickname: (String) -> Unit = {},
) {
    var nickname by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(nickname) {
        updateNickname(nickname)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "닉네임 입력",
                        color = Black,
                        style = Typography.headlineMedium.copy(fontSize = 22.sp),
                    )
                },
                onNavigationClick = popUpBackStack,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "가족에게 불리고 싶은 별명을",
                style = Typography.bodyLarge,
                color = Gray01,
            )
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "설정해 보는 건 어때요?",
                style = Typography.bodyLarge,
                color = Gray01,
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Column {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        value = nickname,
                        onValueChanged = {
                            nickname = it
                        },
                        placeHolder = "닉네임을 입력해 주세요",
                        focusManager = focusManager,
                        maxValue = 5,
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 3.dp, end = 7.dp),
                            text = "${nickname.length}/5",
                            style = Typography.displaySmall,
                            color = Gray02,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            RoundLongButton(
                text = "설정 완료",
                onClick = navigateToPicture,
                enabled = nickname.isNotBlank() && nickname.length <= 5,
            )
        }
    }
}

@Preview
@Composable
fun NicknamePreview() {
    NicknameScreen()
}
