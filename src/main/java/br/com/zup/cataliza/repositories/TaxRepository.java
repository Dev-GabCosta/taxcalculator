package br.com.zup.cataliza.repositories;

import br.com.zup.cataliza.models.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Tax, Long> {
}
