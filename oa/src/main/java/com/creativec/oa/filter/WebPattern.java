package com.creativec.oa.filter;

import lombok.*;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WebPattern {

    private List<String> antPatterns;

}
