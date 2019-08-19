package cn.wolfcode.luowowo.search.service;


import cn.wolfcode.luowowo.search.template.DestinationTemplate; /**
 * 目的地搜索服务
 */
public interface IDestinationSearchService {


    /**
     * 保存
     * @param
     */
    void saveOrUpdate(DestinationTemplate template);

    DestinationTemplate findByName(String keyword);
}
