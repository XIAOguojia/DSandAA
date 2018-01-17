package chapter5;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/24
 * Time:11:02
 */
public interface HashFamily<Anytype> {
    int hash(Anytype x,int which);
    int getNumberOfFunctions();
    void generateNewFunctions();
}
