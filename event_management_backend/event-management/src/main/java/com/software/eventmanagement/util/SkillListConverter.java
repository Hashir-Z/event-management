package com.software.eventmanagement.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.software.eventmanagement.model.Skill;

@Converter(autoApply = true)
public class SkillListConverter implements AttributeConverter<List<Skill>, String> {

    @Override
    public String convertToDatabaseColumn(List<Skill> skills) {
        if (skills == null || skills.isEmpty()) {
            return "";
        }
        return skills.stream().map(Skill::name).collect(Collectors.joining(","));
    }

    @Override
    public List<Skill> convertToEntityAttribute(String skills) {
        if (skills == null || skills.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(skills.split(","))
                .map(Skill::valueOf)
                .collect(Collectors.toList());
    }
}
