package com.cci.safejc.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 java��  @interface Annotation{ } ����һ��ע�� ��һ��ע����һ����

ע��@Retention������������ע�⣬��ע���ע�⣬��ΪԪע��
��@Retention(RetentionPolicy.CLASS)���ε�ע�⣬��ʾע�����Ϣ��������class�ļ�(�ֽ����ļ�)�е��������ʱ�������ᱻ�������ȡ�����е�ʱ��
��@Retention(RetentionPolicy.SOURCE )���ε�ע��,��ʾע�����Ϣ�ᱻ��������������������class�ļ��У�ע�����Ϣֻ������Դ�ļ��У�
��@Retention(RetentionPolicy.RUNTIME )���ε�ע�⣬��ʾע�����Ϣ��������class�ļ�(�ֽ����ļ�)�е��������ʱ���ᱻ���������������ʱ��

**/

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AddFileIndex {
}
