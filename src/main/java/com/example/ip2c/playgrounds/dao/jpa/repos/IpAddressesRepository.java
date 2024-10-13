package com.example.ip2c.playgrounds.dao.jpa.repos;

import com.example.ip2c.playgrounds.dao.jpa.entities.IpAddressEntity;

public interface IpAddressesRepository extends CrudRepository<IpAddressEntity, Integer> {
    IpAddressEntity findByIp(String ip);
}
