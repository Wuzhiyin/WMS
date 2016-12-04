package com.ken.wms.service.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ken.wms.dao.RepositoryAdminMapper;
import com.ken.wms.domain.RepositoryAdmin;
import com.ken.wms.service.Interface.RepositoryAdminManageService;
import com.ken.wms.service.util.ExcelUtil;

/**
 * 仓库管理员管理 Service 实现类
 * 
 * @author Ken
 *
 */
@Service
public class RepositoryAdminManageServiceImpl implements RepositoryAdminManageService {

	@Autowired
	private RepositoryAdminMapper repositoryAdminMapper;
	@Autowired
	private ExcelUtil excelUtil;

	/**
	 * 返回指定repository id 的仓库管理员记录
	 * 
	 * @param repositoryAdminID
	 *            仓库管理员ID
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectByID(Integer repositoryAdminID) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<RepositoryAdmin> repositoryAdmins = new ArrayList<>();
		long total = 0;

		// 查询
		RepositoryAdmin repositoryAdmin = repositoryAdminMapper.selectByID(repositoryAdminID);
		if (repositoryAdmin != null) {
			repositoryAdmins.add(repositoryAdmin);
			total = 1;
		}

		resultSet.put("data", repositoryAdmins);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 返回指定 repository address 的仓库管理员记录 支持查询分页以及模糊查询
	 * 
	 * @param offset
	 *            分页的偏移值
	 * @param limit
	 *            分页的大小
	 * @param name
	 *            仓库管理员的名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectByName(int offset, int limit, String name) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<RepositoryAdmin> repositoryAdmins = null;
		long total = 0;

		// 查询
		PageHelper.offsetPage(offset, limit);
		repositoryAdmins = repositoryAdminMapper.selectByName(name);
		if (repositoryAdmins != null) {
			PageInfo<RepositoryAdmin> pageInfo = new PageInfo<>(repositoryAdmins);
			total = pageInfo.getTotal();
		} else
			repositoryAdmins = new ArrayList<>();

		resultSet.put("data", repositoryAdmins);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 返回指定 repository Name 的仓库管理员记录 支持模糊查询
	 * 
	 * @param name
	 *            仓库管理员名称
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectByName(String name) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<RepositoryAdmin> repositoryAdmins = null;
		long total = 0;

		// 查询
		repositoryAdmins = repositoryAdminMapper.selectByName(name);
		if (repositoryAdmins != null) {
			total = repositoryAdmins.size();
		} else
			repositoryAdmins = new ArrayList<>();

		resultSet.put("data", repositoryAdmins);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 分页查询仓库管理员的记录
	 * 
	 * @param offset
	 *            分页的偏移值
	 * @param limit
	 *            分页的大小
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectAll(int offset, int limit) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<RepositoryAdmin> repositoryAdmins = null;
		long total = 0;

		// 查询
		PageHelper.offsetPage(offset, limit);
		repositoryAdmins = repositoryAdminMapper.selectAll();
		if (repositoryAdmins != null) {
			PageInfo<RepositoryAdmin> pageInfo = new PageInfo<>(repositoryAdmins);
			total = pageInfo.getTotal();
		} else
			repositoryAdmins = new ArrayList<>();

		resultSet.put("data", repositoryAdmins);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 查询所有仓库管理员的记录
	 * 
	 * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 */
	@Override
	public Map<String, Object> selectAll() {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<RepositoryAdmin> repositoryAdmins = null;
		long total = 0;

		// 查询
		repositoryAdmins = repositoryAdminMapper.selectAll();
		if (repositoryAdmins != null) {
			total = repositoryAdmins.size();
		} else
			repositoryAdmins = new ArrayList<>();

		resultSet.put("data", repositoryAdmins);
		resultSet.put("total", total);
		return resultSet;
	}

	/**
	 * 添加仓库管理员信息
	 * 
	 * @param repositoryAdmin
	 *            仓库管理员信息
	 * @return 返回一个boolean值，值为true代表添加成功，否则代表失败
	 */
	@Override
	public boolean addRepositoryAdmin(RepositoryAdmin repositoryAdmin) {

		if (repositoryAdmin != null) {
			if(repositoryAdminCheck(repositoryAdmin)){
				repositoryAdminMapper.insert(repositoryAdmin);
				if(repositoryAdmin.getId() != null)
					return true;
			}
		}

		return false;
	}

	/**
	 * 更新仓库管理员信息
	 * 
	 * @param repositoryAdmin
	 *            仓库管理员信息
	 * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
	 */
	@Override
	public boolean updateRepositoryAdmin(RepositoryAdmin repositoryAdmin) {
		
		if(repositoryAdmin != null && repositoryAdminCheck(repositoryAdmin)){
			// 检查所属仓库是否已经被指派
			if(repositoryAdminMapper.selectByRepositoryID(repositoryAdmin.getRepositoryBelongID()) == null){
				repositoryAdminMapper.update(repositoryAdmin);
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 删除仓库管理员信息
	 * 
	 * @param repositoryAdminID
	 *            仓库管理员ID
	 * @return 返回一个boolean值，值为true代表删除成功，否则代表失败
	 */
	@Override
	public boolean deleteRepositoryAdmin(Integer repositoryAdminID) {
		
		repositoryAdminMapper.deleteByID(repositoryAdminID);
		
		return true;
	}

	/**
	 * 为仓库管理员指派指定 ID 的仓库
	 * 
	 * @param repositoryAdminID
	 *            仓库管理员ID
	 * @param repositoryID
	 *            所指派的仓库ID
	 * @return 返回一个 boolean 值，值为 true 表示仓库指派成功，否则表示失败
	 */
	@Override
	public boolean assignRepository(Integer repositoryAdminID, Integer repositoryID) {
		
		RepositoryAdmin repositoryAdmin = repositoryAdminMapper.selectByID(repositoryAdminID);
		if(repositoryAdmin != null){
			repositoryAdmin.setRepositoryBelongID(repositoryID);
			return updateRepositoryAdmin(repositoryAdmin);
		}
		
		return false;
	}

	/**
	 * 从文件中导入仓库管理员信息
	 * 
	 * @param file
	 *            导入信息的文件
	 * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
	 */
	@Override
	public Map<String, Object> importRepositoryAdmin(MultipartFile file) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		long total = 0;
		long available = 0;
		
		// 从文件中读取
		List<Object> repositoryAdmins = excelUtil.excelReader(RepositoryAdmin.class, file);
		
		if(repositoryAdmins != null){
			total = repositoryAdmins.size();
			
			// 验证记录
			RepositoryAdmin repositoryAdmin;
			List<RepositoryAdmin> availableList = new ArrayList<>();
			for (Object object : repositoryAdmins) {
				repositoryAdmin = (RepositoryAdmin) object;
				if(repositoryAdminCheck(repositoryAdmin))
					availableList.add(repositoryAdmin);
			}
			
			// 保存到数据库
			available = availableList.size();
			if(available > 0)
				repositoryAdminMapper.insertBatch(availableList);
		}
		
		resultSet.put("total", total);
		resultSet.put("available", available);
		return resultSet;
	}

	/**
	 * 导出仓库管理员信息到文件中
	 * 
	 * @param repositoryAdmins
	 *            包含若干条 repository 信息的 List
	 * @return Excel 文件
	 */
	@Override
	public File exportRepositoryAdmin(List<RepositoryAdmin> repositoryAdmins) {
		File file = null;
		
		if(repositoryAdmins != null){
			file = excelUtil.excelWriter(RepositoryAdmin.class, repositoryAdmins);
		}
		
		return file;
	}

	/**
	 * 检验 repositoryAdmin 信息是否有效
	 * @param repositoryAdmin 仓库管理员信息
	 * @return 返回一个 boolean 值，若仓库管理员信息中要求非空均有值，返回 true，否则返回 false
	 */
	private boolean repositoryAdminCheck(RepositoryAdmin repositoryAdmin) {

		if (repositoryAdmin.getName() != null && repositoryAdmin.getSex() != null && repositoryAdmin.getTel() != null
				&& repositoryAdmin.getBirth() != null && repositoryAdmin.getBirth() != null) {
			return true;
		}else
			return false;
	}

	/**
	 * 返回所属指定 repositoryID 的仓库管理员信息
	 * @param repositoryID 仓库ID 其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
	 * @return 返回一个Map，
	 */
	@Override
	public Map<String, Object> selectByRepositoryID(Integer repositoryID) {
		// 初始化结果集
		Map<String, Object> resultSet = new HashMap<>();
		List<RepositoryAdmin> repositoryAdmins = new ArrayList<>();
		long total = 0;
		
		// 查询
		RepositoryAdmin repositoryAdmin = repositoryAdminMapper.selectByRepositoryID(repositoryID);
		if(repositoryAdmin != null){
			repositoryAdmins.add(repositoryAdmin);
			total = 1;
		}
		
		resultSet.put("data", repositoryAdmins);
		resultSet.put("total", total);
		return resultSet;
	}
}
