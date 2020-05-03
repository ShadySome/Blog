package com.lst.blog.web.admin;

import com.lst.blog.po.Type;
import com.lst.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    //每一页默认有10条，根据id按倒序排序
    public String types(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",typeService.ListType(pageable));
    //pageable对象中的内容，一条一条的数据，包括id，title,content等，还有是否是最后一页的标志，总共的数据条数，当前页数。按照什么排序，排序方式，当前页 的数据占总数据的多少。
        return "admin/types";
    }
    //新增按钮处理
    @GetMapping("/types/input")
    public  String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable long id,Model model)//PathVariable是确保参数表中的id和路径中的id一致
    {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }
    // 新增一条分类
    @PostMapping("/types")
    //BindingResult来接收校验结果
    public String post(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes){//valid代表要校验后端的type对象
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            result .rejectValue("name","nameError","不能重复添加分类");//和type里定义的name绑定的错误信息一样
            //name对应type里的name，nameError对应NotBlank，是错误编码， 最后的是错误信息
        }
        if(result.hasErrors())
        {
            return  "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if(t == null){
            redirectAttributes.addFlashAttribute("message","新增失败");
        }else{
            redirectAttributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types"; //不能直接返回types页面，因为没有进行pageable对象的获取，就得不到新增的信息。
    }


    @PostMapping("/types/{id}")
    //BindingResult来接收校验结果
    //BindingResult 和要校验的 Type必须放在一起
    public String editPost(@Valid Type type, BindingResult result,@PathVariable long id, RedirectAttributes redirectAttributes){//valid代表要校验后端的type对象
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            result .rejectValue("name","nameError","不能重复添加分类");//和type里定义的name绑定的错误信息一样
            //name对应type里的name，nameError对应NotBlank，是错误编码， 最后的是错误信息
        }
        if(result.hasErrors())
        {
            return  "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if(t == null){
            redirectAttributes.addFlashAttribute("message","更新失败");
        }else{
            redirectAttributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types"; //不能直接返回types页面，因为没有进行pageable对象的获取，就得不到新增的信息。
    }

        @GetMapping("/types/{id}/delete")
        public String delete(@PathVariable long id,RedirectAttributes redirectAttributes)
        {
            typeService.deleteType(id);
            redirectAttributes.addFlashAttribute("message","删除成功");
            return "redirect:/admin/types";
        }

}
