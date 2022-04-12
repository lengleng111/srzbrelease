package org.jeecg.modules.srzb.employee.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.employee.dao.dto.BranchDto;
import org.jeecg.modules.srzb.employee.dao.entity.SrBranchEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 店铺信息表 服务类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-16
 */
public interface ISrBranchService extends IService<SrBranchEntity> {

    Result<?> insertBranch(BranchDto branchDto);
    Result<?> deleteBranch(Long id);
    Result<?> updateBranch(BranchDto branchDto);

    Result<?> selectOne(Long id);

    Result<?> queryAllBranch(int pageNo, int pageSize, String branchName);
}
