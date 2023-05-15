package flow.cp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String token;
	private String email;
	private String username;
	private String password;
	//고유번호
	private String member_id;
}
