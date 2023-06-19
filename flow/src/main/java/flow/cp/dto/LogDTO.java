package flow.cp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
	private String id;
	private String token;
	private String url1;
	private String url2;
	private String title1;
	private String title2;
	private String member_id;
	private float imgrate;
	private float textrate;
	private float totalrate;
}
