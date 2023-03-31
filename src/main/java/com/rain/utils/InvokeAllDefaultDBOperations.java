package com.rain.utils;

import com.rain.process.subscription.DefaultTask;

public class InvokeAllDefaultDBOperations {

	public static void invokeAllDefaultTask()
	{
		DefaultTask.invokeDefaultSubScription();
		DefaultTask.invokeDefaultRole();
	}
}
