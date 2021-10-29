package com.AppcentTodoApp.controller;


import com.AppcentTodoApp.model.Task;
import com.AppcentTodoApp.service.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class TaskController {
    TaskService taskService;

    @ApiOperation(value = "Yeni bir görev oluşturma")
    @ApiResponses( value ={
            @ApiResponse(code = 201, message = "Görev başarıyla oluşturuldu"),
            @ApiResponse(code = 500, message = "Görev oluşturulurken bir hata oluştu, bilgileri kontrol edin")

    })

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        log.info("Bilgilerle yeni bir görev oluşturma [{}]", task);
        return taskService.createTask(task);
    }



    @ApiOperation(value = "Tüm görevleri listeleme")
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "Görevler başarıyla listelendi"),
            @ApiResponse(code = 500, message = "Görevler listelenirken bir hata oluştu.")

    })
    @GetMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks() {
        log.info("Tüm kayıtlı görevleri listeleme");
        return taskService.listAllTasks();
    }

    @ApiOperation(value = "Kimliğe göre görev arama")
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "Görev başarıyla bulundu"),
            @ApiResponse(code = 404, message = "Bu kimliğe sahip görev bulunamadı")

    })
    @GetMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Task> getTaskById(@PathVariable (value = "id") Long id) {
        log.info("id ile arama görevi [{}]", id);
        return taskService.findTaskById(id);
    }

    @ApiOperation(value = "Görev güncelleme")
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "Görev başarıyla güncellendi"),
            @ApiResponse(code = 404, message = "Görev güncellenemiyor - görev bulunamadı")

    })
    @PutMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Task> updateTaskById(@PathVariable (value = "id") Long id, @RequestBody Task task) {
        log.info("[{}] kimliğine sahip görev güncellenirken yeni bilgi şu şekildedir: [{}]",id, task);

        return taskService.updateTaskById(task,id);
    }


    @ApiOperation(value = "Görev Silme")
    @ApiResponses( value ={
            @ApiResponse(code = 204, message = "Görev başarıyla silindi"),
            @ApiResponse(code = 404, message = " Görev silinemiyor - görev bulunamadı")

    })
    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteTaskById(@PathVariable (value = "id") Long id) {
        log.info("[{}] kimliğine sahip görevleri silme", id);
        return taskService.deleteById(id);
    }
}
