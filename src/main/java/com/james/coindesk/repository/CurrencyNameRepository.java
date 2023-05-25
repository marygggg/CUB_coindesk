package com.james.coindesk.repository;

import com.james.coindesk.repository.entity.CurrencyNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyNameRepository extends JpaRepository<CurrencyNameEntity, String> {

}