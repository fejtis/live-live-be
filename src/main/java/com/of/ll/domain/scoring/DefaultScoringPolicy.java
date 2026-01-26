package com.of.ll.domain.scoring;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

public interface DefaultScoringPolicy {

    int score(Activity activity, Context context);

}
