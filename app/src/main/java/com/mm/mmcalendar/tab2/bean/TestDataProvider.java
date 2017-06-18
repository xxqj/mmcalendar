package com.mm.mmcalendar.tab2.bean;

import com.mm.mmcalendar.bmobmodel.CategoryBean;
import com.mm.mmcalendar.bmobmodel.InfoBean;
import com.mm.mmcalendar.bmobmodel.SubCategoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/30.
 */
public class TestDataProvider {
      public static List<CategoryBean> getCategoryS(){
          List<CategoryBean> categoryBeanList=new ArrayList<CategoryBean>();
          for(int i=0;i<5;i++){
              CategoryBean bean=new CategoryBean();
              bean.setCategoryId(String.valueOf(i));
              bean.setCategoryName("标题"+i);
              categoryBeanList.add(bean);
          }
          return  categoryBeanList;
      }

    public static CategoryBean getCategory(String categoryId){
        CategoryBean bean=new CategoryBean();
        bean.setCategoryId(categoryId);
        bean.setCategoryName("标题"+categoryId);
        return  bean;
    }


      public static SubCategoryBean getSubCategory(String subCategoryId){

          SubCategoryBean bean=new SubCategoryBean();
          bean.setCategoryId(subCategoryId);
          bean.setCategoryName("分组"+subCategoryId);
          bean.setCategoryBean(getCategory(subCategoryId));

          return  bean;
      }

     public static List<InfoBean> getInfoS(String categoryId){
         List<InfoBean> infoBeanList=new ArrayList<InfoBean>();
         int len=Integer.parseInt(categoryId)+1;
         for(int i=0;i<len;i++){
             InfoBean bean=new InfoBean();
            // bean.setCategoryId(String.valueOf(i));
             bean.setInfoId(String.valueOf(i));
             bean.setInfoTitle("内容"+i);
             bean.setInfoDesc("精彩内容点击阅读……");
             if(i%2==0){
                // bean.setSubCategoryBean(getSubCategory("0"));
             }else{
                // bean.setSubCategoryBean(getSubCategory("1"));
             }
             infoBeanList.add(bean);
         }
         return  infoBeanList;
     }
}
