package tt.trialTales.member;

public record MemberProfileResponse(
        String username,
        String nickname,
        Role role
) {
}
