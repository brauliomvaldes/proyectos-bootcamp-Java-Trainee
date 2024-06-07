package bmva.digiwallet.dto;

import bmva.digiwallet.models.User_;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactoDto {

	private User_ user;
	private String number;
	private String alias;
	private String bank;
	private String currencyy;
	private boolean state;
}
