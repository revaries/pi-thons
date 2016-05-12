from django.conf.urls import include, url

from django.contrib import admin
admin.autodiscover()
from rest_framework.urlpatterns import format_suffix_patterns
from apis import views
import hello.views
import dashboard.views
# Examples:
# url(r'^$', 'gettingstarted.views.home', name='home'),
# url(r'^blog/', include('blog.urls')),

urlpatterns = [
    url(r'^$', dashboard.views.index, name='index'),
    url(r'^admin/', include(admin.site.urls)),
    url(r'^attendance/$',views.markattendance,name="MarkAttendance"),
    url(r'^register/$',views.registerstudent,name="RegisterStudent"),
]
