package com.of.ll.domain.scoring;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

public interface ScoringPolicy {

    int score(Activity activity, Context context);

}
