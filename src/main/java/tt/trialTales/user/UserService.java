package tt.trialTales.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 생성
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // UserRequestDTO로부터 User 객체 생성
        UserId userId = new UserId(userRequestDTO.name(), userRequestDTO.email(), userRequestDTO.role());

        // 사용자 저장
        UserId savedUserId = userRepository.save(userId);

        // 저장된 사용자 정보를 UserResponseDTO로 반환
        return new UserResponseDTO(savedUserId.getId(), savedUserId.getName(), savedUserId.getEmail(), savedUserId.getRole());
    }

    // 모든 사용자 조회
    public List<UserResponseDTO> getAllUsers() {
        // 모든 사용자 리스트 조회
        List<UserId> userIds = userRepository.findAll();

        // User 객체들을 UserResponseDTO로 변환하여 반환
        return userIds.stream()
                .map(userId -> new UserResponseDTO(userId.getId(), userId.getName(), userId.getEmail(), userId.getRole()))
                .toList();
    }

    // ID로 사용자 조회
    public UserResponseDTO getUserById(Long id) {
        // ID로 사용자 조회
        UserId userId = userRepository.findById(id).orElse(null);

        // 사용자가 없으면 null 반환
        if (userId == null) {
            return null;
        }

        // 조회된 사용자 정보를 UserResponseDTO로 반환
        return new UserResponseDTO(userId.getId(), userId.getName(), userId.getEmail(), userId.getRole());
    }

    // 사용자 수정
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        // ID로 기존 사용자 조회
        UserId existingUserId = userRepository.findById(id).orElse(null);

        // 사용자가 존재하지 않으면 null 반환
        if (existingUserId == null) {
            return null;
        }

        // 기존 사용자 정보를 새로운 데이터로 업데이트
        UserId updatedUserId = new UserId(existingUserId.getId(), userRequestDTO.name(), userRequestDTO.email(), userRequestDTO.role());

        // 업데이트된 사용자 정보 저장
        userRepository.save(updatedUserId);

        // 업데이트된 사용자 정보를 UserResponseDTO로 반환
        return new UserResponseDTO(updatedUserId.getId(), updatedUserId.getName(), updatedUserId.getEmail(), updatedUserId.getRole());
    }

    // 사용자 삭제
    @Transactional
    public void deleteUser(Long id) {
        // ID로 사용자가 존재하는지 확인
        if (userRepository.existsById(id)) {
            // 사용자 삭제
            userRepository.deleteById(id);
        }
    }
}
