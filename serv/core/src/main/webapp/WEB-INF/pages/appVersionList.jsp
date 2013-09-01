<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="appVersionList.title"/></title>
    <meta name="menu" content="AppVersionMenu"/>
</head>

<div class="span10">
    <h2><fmt:message key="appVersionList.heading"/></h2>

    <form method="get" action="${ctx}/appVersions" id="searchForm" class="form-search">
    <div id="search" class="input-append">
        <input type="text" size="20" name="q" id="query" value="${param.q}"
               placeholder="<fmt:message key="search.enterTerms"/>" class="input-medium search-query"/>
        <button id="button.search" class="btn" type="submit">
            <i class="icon-search"></i> <fmt:message key="button.search"/>
        </button>
    </div>
    </form>

    <fmt:message key="appVersionList.message"/>

    <div id="actions" class="form-actions">
        <a class="btn btn-primary" href="<c:url value='/editAppVersion'/>" >
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/>
        </a>
        <a class="btn" href="<c:url value="/mainMenu"/>" >
            <i class="icon-ok"></i> <fmt:message key="button.done"/>
        </a>
    </div>

    <display:table name="appVersions" class="table table-condensed table-striped table-hover" requestURI="" id="appVersionList" export="true" pagesize="25">
        <display:column property="id" media="csv excel xml pdf" titleKey="appVersion.id"/>
        <display:column property="num" sortable="true" titleKey="appVersion.num" url="editAppVersion" media="html pdf"
            paramId="id" paramProperty="id" />
       <display:column property="title" sortable="true" titleKey="appVersion.title"  url="editAppVersion" media="html pdf" 
            paramId="id" paramProperty="id" />
         <display:column property="releaseDate" sortable="true" titleKey="appVersion.releaseDate" format="{0,date,yyyy-MM-dd}"/>
        <display:column property="url"  sortable="true" titleKey="appVersion.url" autolink="true"></display:column>
        <display:column property="mustUpdate" sortable="true" titleKey="appVersion.mustUpdate"/>

        <display:setProperty name="paging.banner.item_name"><fmt:message key="appVersionList.appVersion"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="appVersionList.appVersions"/></display:setProperty>

        <display:setProperty name="export.excel.filename"><fmt:message key="appVersionList.title"/>.xls</display:setProperty>
        <display:setProperty name="export.csv.filename"><fmt:message key="appVersionList.title"/>.csv</display:setProperty>
        <display:setProperty name="export.pdf.filename"><fmt:message key="appVersionList.title"/>.pdf</display:setProperty>
    </display:table>
</div>
