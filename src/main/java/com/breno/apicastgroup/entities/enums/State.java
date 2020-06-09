package com.breno.apicastgroup.entities.enums;

public enum State {
	
	 AMAZONAS(1), 
	  ALAGOAS(2), 
	  ACRE(3),
	  AMAPA(4), 
	  BAHIA(5), 
	  PARA(6),
	  MATO_GROSSO(7), 
	  MINAS_GERAIS(8),
	  MATO_GROSSO_DO_SUL(9), 
	  GOIAS(10),
	  MARANHAO(11), 
	  RIO_GRANDE_DO_SUL(12),
	  TOCANTINS(13), 
	  PIAUI(14), 
	  SAO_PAULO(15),
	  RONDONIA(16), 
	  RORAIMA(17),
	  PARANA(18), 
	  CEARA(19), 
	  PERNAMBUCO(20),
	  SANTA_CATARINA(21), 
	  PARAIBA(22),
	  RIO_GRANDE_DO_NORTE(23), 
	  ESPIRITO_SANTO(24),
	  RIO_DE_JANEIRO(25),
	  SERGIPE(26);
	
	private int code;

	private State(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static State valueOf(int code) {
		for(State value : State.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		
		throw new IllegalArgumentException("Invalid State Code");
	}
}
