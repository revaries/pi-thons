# -*- coding: utf-8 -*-
# Generated by Django 1.9.6 on 2016-05-11 23:58
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('apis', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='HelloGreeting',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('when', models.DateTimeField()),
            ],
            options={
                'db_table': 'hello_greeting',
                'managed': False,
            },
        ),
    ]
