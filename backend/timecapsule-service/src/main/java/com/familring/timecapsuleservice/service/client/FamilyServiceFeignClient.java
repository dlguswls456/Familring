package com.familring.timecapsuleservice.service.client;

import com.familring.common_module.dto.BaseResponse;
import com.familring.timecapsuleservice.dto.client.Family;
import com.familring.timecapsuleservice.dto.client.FamilyStatusRequest;
import com.familring.timecapsuleservice.dto.client.UserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "family-service")
public interface FamilyServiceFeignClient {
    @GetMapping("/client/family")
    BaseResponse<Family> getFamilyInfo(@RequestParam Long userId);

    @GetMapping("/client/family/member")
    BaseResponse<List<UserInfoResponse>> getFamilyMemberList(@RequestParam Long userId);

    @PutMapping("/client/family/status")
    void updateFamilyStatus(@RequestBody FamilyStatusRequest familyStatusRequest);
}
