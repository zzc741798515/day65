package cn.nyse.service.impl;

import cn.nyse.entity.Waste;
import cn.nyse.service.WasteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WasteServiceImpl extends ServiceImpl<Waste> implements WasteService {


}
