package com.story.storySingularity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.story.storySingularity.mapper.PostsMapper;
import com.story.storySingularity.mapper.UsersMapper;
import com.story.storySingularity.model.dto.PostsListDto;
import com.story.storySingularity.model.po.Posts;
import com.story.storySingularity.model.po.Users;
import com.story.storySingularity.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaban
 */
@Slf4j
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

    @Autowired
    private PostsMapper postsMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Page<Posts> listByPostsListDto(PostsListDto postsListDto) {
        ArrayList<Posts> posts = new ArrayList<>();
        //要搜索的关键字
        String key = postsListDto.getKey();
        LambdaQueryWrapper<Users> usersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Posts> postsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        usersLambdaQueryWrapper.like(Users::getName, key);
        //找到搜寻的用户
        List<Users> users = usersMapper.selectList(usersLambdaQueryWrapper);
        if (users.size() != 0){
            //找到则遍历每一个用户
            for (int i = 0; i < users.size(); i++) {
                postsLambdaQueryWrapper = new LambdaQueryWrapper<>();
                //获取用户id
                Integer userId = users.get(i).getId();
                postsLambdaQueryWrapper.eq(Posts::getUserId,userId);
                //找到用户id发的帖子
                List<Posts> postsByUserId = postsMapper.selectList(postsLambdaQueryWrapper);
                if (postsByUserId != null){
                    posts.addAll(postsByUserId);
                }
            }
            postsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        }
        //在标题中寻找
        postsLambdaQueryWrapper.like(Posts::getTitle,key);
        List<Posts> postsBySelectTitle = postsMapper.selectList(postsLambdaQueryWrapper);
        if (postsBySelectTitle.size() != 0){
            posts.addAll(postsBySelectTitle);
        }
        //在内容中寻找
        postsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        postsLambdaQueryWrapper.like(Posts::getContent,key);
        List<Posts> postsBySelectContent = postsMapper.selectList(postsLambdaQueryWrapper);
        if (postsBySelectContent.size() != 0){
            posts.addAll(postsBySelectContent);
        }
        for(Posts p : posts){
            System.out.println(p);
        }
        //通过用户id，内容，标题查找到的posts中可能存在重复的，需要进行清洗
        List<Posts> postsSorted = posts.stream().distinct().sorted(Comparator.comparing(Posts::getUserId))
                .collect(Collectors.toList());
        for(Posts p : postsSorted){
            System.out.println(p);
        }
        Integer pageNo = postsListDto.getPageNo();
        Integer pageNum = postsListDto.getPageNum();
        int size = postsSorted.size();
        //设置最终的返回的分页
        Page<Posts> postsPage = new Page<>();
        //存储分页后的集合
        ArrayList<Posts> postsByPage = new ArrayList<>();
        //1 5   0 1 2 3 4
        //2 5   5 6 7 8 9
        if (size <= 5){
            postsByPage.addAll(postsSorted);
        }
        else{
            if (pageNo*pageNum - 1 > size){
                for (int i = pageNo*pageNum-pageNum; i < size; i++) {
                    if (postsSorted.get(i) != null){
                        postsByPage.add(postsSorted.get(i));
                    }
                }
            }else{
                for (int i = pageNo*pageNum-pageNum; i < pageNo*pageNum - 1; i++) {
                    if (postsSorted.get(i) != null){
                        postsByPage.add(postsSorted.get(i));
                    }
                }
            }
        }

        postsPage.setRecords(postsByPage);
        postsPage.setCurrent(pageNo);
        postsPage.setSize(pageNum);
        postsPage.setTotal(size);
        return postsPage;
    }
}
