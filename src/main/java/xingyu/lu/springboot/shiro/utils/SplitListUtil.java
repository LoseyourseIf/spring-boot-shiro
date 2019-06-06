package xingyu.lu.springboot.shiro.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingyu.lu
 * @create 2018-03-28 17:27
 **/
public class SplitListUtil {

    /**
     * 列表分组
     * @author xingyu.lu
     */
    public static <T> List<List<T>> dividedListByQuantity(List<T> list, int quantity) {
        List<List<T>> dividedList = new ArrayList<>();
        if (list == null || list.size() == 0) {
            dividedList.add(list);
            return dividedList;
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Wrong Quantity");
        }

        int count = 0;
        while (count < list.size()) {
            dividedList.add(
                    list.subList(count, (count + quantity) > list.size() ?
                            list.size() :
                            count + quantity));
            count += quantity;
        }

        return dividedList;
    }

    public static <T> String getListString(List<T> list) {
        if (list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (T o : list) {
            sb.append(o.toString()).append(",");
        }
        return sb.toString();
    }
}
