package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
