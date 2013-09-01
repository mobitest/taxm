<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="appVersionDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='appVersionDetail.heading'/>"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="appVersionList.appVersion"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="span2">
    <h2><fmt:message key="appVersionDetail.heading"/></h2>
    <fmt:message key="appVersionDetail.message"/>
</div>

<div class="span7">
    <s:form id="appVersionForm" action="saveAppVersion" method="post" validate="true" cssClass="well form-horizontal">
        <s:hidden key="appVersion.id" required="true"/>
        <s:textfield key="appVersion.num" required="true" maxlength="3" />
        <s:textfield key="appVersion.title" required="true" maxlength="100" />
        <s:textfield key="appVersion.mustUpdate" required="true" maxlength="1"  value="1"/>
        <s:textfield key="appVersion.url" required="true" maxlength="200" />
        <s:textfield key="appVersion.releaseDate" required="false" maxlength="10" />
        <s:textfield key="appVersion.releaseNotes" required="false" maxlength="800" />
        <s:textfield key="appVersion.remark" required="false" maxlength="400" />
                    
        <div id="actions" class="form-actions">
            <s:submit type="button" cssClass="btn btn-primary" method="save" key="button.save" theme="simple">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
            </s:submit>
            <c:if test="${not empty appVersion.id}">
                <s:submit type="button" cssClass="btn btn-warning" method="delete" key="button.delete"
                    onclick="return confirmMessage(msgDelConfirm)" theme="simple">
                    <i class="icon-trash icon-white"></i> <fmt:message key="button.delete"/>
                </s:submit>
            </c:if>
            <s:submit type="button" cssClass="btn" method="cancel" key="button.cancel" theme="simple">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </s:submit>
        </div>
    </s:form>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['appVersionForm']).focus();
    });
</script>
