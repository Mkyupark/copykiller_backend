package flow.cp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextResultDTO {
	private String logid;
	private String timeline1;
	private String timeline2;
	private float copyrate;
}
