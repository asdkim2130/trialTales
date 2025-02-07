package tt.trialTales.Application;

import java.time.LocalDateTime;

public record UpdateApplicationResponse(Long id,
                                        String email,
                                        String snsUrl,
                                      LocalDateTime applicationDate,
                                      Status status) {
}
