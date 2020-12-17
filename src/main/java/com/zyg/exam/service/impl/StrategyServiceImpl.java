package com.zyg.exam.service.impl;

import com.zyg.exam.common.AllRandom;
import com.zyg.exam.common.DTO.AddPaperDTO;
import com.zyg.exam.common.DTO.StrategyDTO;
import com.zyg.exam.common.JsonBean;
import com.zyg.exam.dao.ConditionDao;
import com.zyg.exam.dao.PaperDao;
import com.zyg.exam.dao.QuestionDao;
import com.zyg.exam.dao.StrategyDao;
import com.zyg.exam.model.Condition;
import com.zyg.exam.model.Paper;
import com.zyg.exam.model.Strategy;
import com.zyg.exam.service.StrategyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class StrategyServiceImpl extends  AllRandom implements StrategyService {
    @Autowired
    private StrategyDao strategyDao;
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private ConditionDao conditionDao;

    @Override
    public JsonBean insertStrategy(Strategy strategy) {
        int num =0;
        try {
            num=strategyDao.insertSelective(strategy);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (num>0){
            return new JsonBean(200,null,"添加成功");
        }else {
            return new JsonBean(500,null,"添加失败");
        }
    }

    @Override
    public JsonBean formPaper(AddPaperDTO addPaperDTO) {
        System.out.println("addPaperDTO"+addPaperDTO);
        Paper paper = Paper.builder().
                courseid(addPaperDTO.getCourseid()).
                finishtime(addPaperDTO.getFinishtime()).
                starttime(addPaperDTO.getStarttime()).
                pattern(addPaperDTO.getPattern()).
                ismonitor(addPaperDTO.getIsmonitor()).
                build();
            paperDao.insert(paper);
        List<StrategyDTO> strategyDTOS = addPaperDTO.getStrategyDTOS();
        List<Integer> questionids = new ArrayList<>();
        //循环判断考试策略
        try {
            for (StrategyDTO strategyDTO : strategyDTOS) {
                if (strategyDTO.getMode() == 1) {//全随机
                    List<List<Integer>> questions = new ArrayList<>();
                    System.out.println("全随机模式");
                    Strategy strategy = new Strategy();
                    //过去题目类型
                    String type = strategyDTO.getType();
                    //获取题目数量
                    int count = strategyDTO.getCount();
                    //获取题目分值
                    int value = strategyDTO.getScore();
                    //获取paperid
                    int paperid = paper.getPaperId();
                    //获取策略类型
                    int mode = strategyDTO.getMode();
                    strategy.setValue(value);
                    strategy.setType(type);
                    strategy.setPaperid(paperid);
                    strategy.setCount(count);
                    strategy.setMode(mode);
                    System.out.println("strategy"+strategy);
                    //插入策略表
                    List<Integer> question1 = questionDao.selectByType(type);
                    //获取随机后的题目id列表
                    questionids = this.randomQuestion(question1, count);
                    System.out.println("随机后的试题"+questionids);
                    questions.add(questionids);
                    strategyDao.insertSelective(strategy);
                    this.insertPaperQuestion(questions, paper.getPaperId());
                } else if (strategyDTO.getMode() == 2) {//按章节，难度组卷
                    List<List<Integer>> questions = new ArrayList<>();
                    System.out.println("插入前的questions"+questions);
                    System.out.println("按章节，难度");
                    int count = strategyDTO.getCount();
                    int chapterid = strategyDTO.getChapterid();
                    String difficulty = strategyDTO.getDifficulty();
                    String type = strategyDTO.getType();
                    List<Integer> question1 = questionDao.selectByChapter(difficulty, chapterid, type);
                    System.out.println(question1);
                    //插入策略表
                    Strategy strategy = new Strategy();
                    strategy.setMode(strategyDTO.getMode());
                    strategy.setCount(count);
                    strategy.setPaperid(paper.getPaperId());
                    strategy.setType(type);
                    strategy.setValue(strategyDTO.getScore());
                    System.out.println("strategy"+strategy);
                    strategyDao.insertSelective(strategy);
                    //插入条件表
                    Condition condition = new Condition();
                    condition.setStrategyid(strategy.getStrategyid());
                    condition.setChapterid(chapterid);
                    condition.setCount(count);
                    condition.setDifficulty(difficulty);
                    System.out.println("condition"+condition);
                    conditionDao.insertSelective(condition);
                    questionids = this.randomQuestion(question1, count);
                    System.out.println("随机后的试题"+questionids);
                    questions.add(questionids);
                    System.out.println("questions"+questions);
                    this.insertPaperQuestion(questions, paper.getPaperId());
                } else if (strategyDTO.getMode() == 3) {//手动组卷
                    List<List<Integer>> questions = new ArrayList<>();
                    System.out.println("手动组卷");
                    Strategy strategy = new Strategy();
                    strategy.setMode(strategyDTO.getMode());
                    strategy.setCount(strategyDTO.getCount());
                    strategy.setPaperid(paper.getPaperId());
                    strategy.setType(strategyDTO.getType());
                    strategy.setValue(strategyDTO.getScore());
                    strategyDao.insertSelective(strategy);
                    int[] questionid = strategyDTO.getQuestionids();
                    for (int i = 0; i < questionid.length; i++) {
                        paperDao.insertPaperQuestion(questionid[i], paper.getPaperId());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }
}
