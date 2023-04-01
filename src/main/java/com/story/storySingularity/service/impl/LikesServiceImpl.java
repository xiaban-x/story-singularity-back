package com.story.storySingularity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.story.storySingularity.mapper.LikesMapper;
import com.story.storySingularity.model.po.Likes;
import com.story.storySingularity.service.LikesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaban
 */
@Slf4j
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements LikesService {

}
