/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package employee.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import employee.rest.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeControllerWebTests {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGet() throws Exception {
        mvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.number").exists())
                .andExpect(jsonPath("$.size").exists())
                .andExpect(jsonPath("$.totalPages").exists())
                .andExpect(jsonPath("$.totalElements").exists())
        ;
    }

    @Test
    public void testCreate() throws Exception {
        final Employee from = Employee.builder()
                .name("new")
                .company("company")
                .position("position")
                .build();

        MvcResult result = mvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(from)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Employee created = employeeFromJson(result);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getCompany()).isEqualTo(from.getCompany());
        assertThat(created.getPosition()).isEqualTo(from.getPosition());
        assertThat(created.getDismissalDate()).isEqualTo(from.getDismissalDate());
        assertThat(created.getEmploymentDate()).isEqualTo(from.getEmploymentDate());
        assertThat(created.getCreateDate()).isNotNull();

    }

    @Test
    public void testUpdate() throws Exception {

        final String originalName = "base_employee";
        final String modifiedName = "modified_employee";

        MvcResult result = mvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(Employee.builder().name(originalName).build())))
                .andExpect(status().isCreated())
                .andReturn();

        Employee created = employeeFromJson(result);

        assertThat(created.getName()).isEqualTo(originalName);

        mvc.perform(
                post("/employee/{id}", created.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(Employee.builder().name(modifiedName).build()))
        )
                .andExpect(status().isResetContent())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(modifiedName))
                .andReturn();

    }

    @Test
    public void testDelete() throws Exception {

        MvcResult result = mvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(Employee.builder().name("delete").build())))
                .andExpect(status().isCreated())
                .andReturn();

        Long employeeId = employeeFromJson(result).getId();

        mvc.perform(delete("/employee/{id}", employeeId))
                .andExpect(status().isNoContent());

        mvc.perform(get("/employee/{id}", employeeId))
                .andExpect(status().isNotFound());

    }

    private Employee employeeFromJson(final MvcResult result) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), Employee.class);
    }

}
