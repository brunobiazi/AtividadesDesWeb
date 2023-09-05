package br.ufscar.dc.dsw.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.model.UsuarioGeral;
import br.ufscar.dc.dsw.service.spec.IUsuarioGeralService;

@Component
public class UniqueCPFValidator implements ConstraintValidator<UniqueCPF, String> {

	@Autowired
	    private IUsuarioGeralService usuarioGeralService;

	@Override
	public boolean isValid(String CPF, ConstraintValidatorContext context) {
		if (usuarioGeralService != null) {
			UsuarioGeral usuarioGeral = usuarioGeralService.buscarPorCpfCnpj(CPF);
			return usuarioGeral == null;
		} else {
			return true;
		}
	}
}