package com.appsmartbusiness.seguranca.acesso;

import javax.ejb.Stateless;

import com.appsmartbusiness.seguranca.acesso.dto.AutorizacaoDTO;
import com.appsmartbusiness.seguranca.acesso.interfaces.IAutenticacao;

@Stateless
public class AutenticadorImp implements IAutenticacao{

	@Override
	public AutorizacaoDTO autenticar() {
		// TODO Auto-generated method stub
		return null;
	}

}
