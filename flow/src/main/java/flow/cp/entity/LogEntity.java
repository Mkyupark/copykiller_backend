package flow.cp.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "youtube_log")
public class LogEntity {
	@Id
	@GeneratedValue(generator ="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	private String id;

	//로그기록이 회원을 조회하는 일이 없어서 그냥 단일관계로 선언하였음.
	@ManyToOne
	@JoinColumn(name = "memeber_id")
	private UserEntity user;
	
	@Column (nullable = false, length = 2024)
	private String url1;
	
	@Column (nullable = false, length = 2024)
	private String url2;
	
	private float textrate;
	
	private float imgrate;
	
	private float totalrate;
}
