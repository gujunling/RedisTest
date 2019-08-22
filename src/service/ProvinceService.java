package service;

import domain.Province;

import java.util.List;

/**
 * @author gjq
 * @create 2019-08-22-17:42
 */
public interface ProvinceService {

    public List<Province> findAll();

    public String findAllJson();

}
