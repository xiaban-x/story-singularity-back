package com.story.storySingularity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.story.storySingularity.mapper.BookmarksMapper;
import com.story.storySingularity.model.po.Bookmarks;
import com.story.storySingularity.service.BookmarksService;
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
public class BookmarksServiceImpl extends ServiceImpl<BookmarksMapper, Bookmarks> implements BookmarksService {

}
