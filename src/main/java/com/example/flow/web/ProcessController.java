package com.example.flow.web;

import com.example.flow.domain.ProcessInstance;
import com.example.flow.domain.ProcessTemplate;
import com.example.flow.engine.ApprovalCommand;
import com.example.flow.engine.ProcessRuntime;
import com.example.flow.engine.StartProcessCommand;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/processes")
public class ProcessController {
    private final ProcessRuntime processRuntime;

    public ProcessController(ProcessRuntime processRuntime) { this.processRuntime = processRuntime; }

    @PostMapping("/templates")
    public ProcessTemplate deploy(@RequestBody String definitionJson) { return processRuntime.deploy(definitionJson); }

    @PostMapping("/instances")
    public ProcessInstance start(@RequestBody StartProcessCommand command) { return processRuntime.start(command); }

    @PostMapping("/instances/{id}/approve")
    public ProcessInstance approve(@PathVariable String id, @RequestBody ApprovalCommand command) { return processRuntime.approve(id, command); }

    @PostMapping("/instances/{id}/reject")
    public ProcessInstance reject(@PathVariable String id, @RequestBody ApprovalCommand command) { return processRuntime.reject(id, command); }
}
