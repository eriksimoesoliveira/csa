package com.eriks.core.objects.tasks

class ProgressionTask(val id: String,
                      val taskEnum: ProgressionTaskEnum,
                      val description: String,
                      val rewardValue: Int,
                      var isClaimed: Boolean,
                      var isCompleted: Boolean,
                      val milestone: Int) {
}