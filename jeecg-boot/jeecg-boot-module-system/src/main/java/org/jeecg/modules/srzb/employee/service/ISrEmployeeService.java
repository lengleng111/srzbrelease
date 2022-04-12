package org.jeecg.modules.srzb.employee.service;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srzb.employee.dao.dto.EmployeeDto;
import org.jeecg.modules.srzb.employee.dao.entity.SrEmployeeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 员工信息表 服务类
 * </p>
 *
 * @author LZQ
 * @since 2022-03-14
 */
public interface ISrEmployeeService extends IService<SrEmployeeEntity> {
    Result<?> insertEmp(EmployeeDto employeeDto);
    Result<?> deleteEmp(Long id);
    Result<?> updateEmp(EmployeeDto employeeDto);

    Result<?> selectOne(Long id);

    Result<?> queryAllEmp(int pageNo, int pageSize, String name, String idcard, String branch);
}
