package tt.trialTales.campaign;

import java.time.LocalDateTime;


//캠페인 생성 및 수정 요청 데이터를 담는 DTO
public record CampaignRequestDto(
        String campaignName,         // String: 캠페인 이름
        String description,          // String: 캠페인 설명
        LocalDateTime startDate,     // LocalDateTime: 캠페인 시작 날짜
        LocalDateTime endDate,       // LocalDateTime: 캠페인 종료 날짜
        String status,               // String: 모집 상태 (예: "모집 중", "모집 완료")
        int recruitmentLimit         // int: 모집 인원 제한
) {}
