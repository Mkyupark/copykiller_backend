package flow.cp.entity;

import java.util.Collection;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class UserEntity {
	@Id
	@GeneratedValue(generator ="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	private String id;
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String password;	
	
////	(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@ManyToOne
//	@JoinColumn(name = "member_id")
//	private LogEntity logentity;
}
