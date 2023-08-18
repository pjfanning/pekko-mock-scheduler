package com.github.pjfanning.pekko.scheduler.mock

import scala.concurrent.duration.FiniteDuration

private[mock] case class Task(delay: FiniteDuration, id: Long, runnable: Runnable, interval: Option[FiniteDuration])
  extends Ordered[Task] {

  def compare(t: Task): Int =
    if (delay > t.delay) -1
    else if (delay < t.delay) 1
    else if (id > t.id) -1
    else if (id < t.id) 1
    else 0

}