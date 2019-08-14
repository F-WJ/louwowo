package cn.wolfcode.luowowo.comment.repository;



import cn.wolfcode.luowowo.comment.domain.StrategyComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 使用MongoDB 的jpa规范处理crud操作
 *
 * 1: 必须集成MongoRepository 接口
 * 2: 指定操作类型 StrategyComment, 可以通过类型获取文档名字, 还有文档下面一些字段
 * 3: 指定id的类型: String   即 贴有这个 @Id 标签的字段类型
 *
 */
@Repository
public interface IStrategyCommentRepository extends MongoRepository<StrategyComment, String> {
}
