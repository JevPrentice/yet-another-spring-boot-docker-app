package com.jevprentice.service;

public class Time {

    private static int func(final int start, final int num) {
	int result = start + num;

	if (result < 24)
	    return result;

	while (result > 24)
	    result = result - 24;

	if (result > 24)
	    throw new RuntimeException(String.format("%d is greater than 24", result));

	return result;
    }

    public static void main(final String[] args) {
	final int start = 22;
	final int num = 16;
	final int result = func(start, num);
	System.out.printf("If it was %d:00 and I waited for %d hours it would then be %d:00",
		start, num, result);
    }
}
