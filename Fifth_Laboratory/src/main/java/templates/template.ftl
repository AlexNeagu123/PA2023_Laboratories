<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
</head>
    <body>
        <p> These are the documents contained in the '${catalog.name}' catalog </p>
        <ol>
        <#list catalog.documents as document>
            <li>
                <ul>
                    <li>Document's title: <b>${document.title}</b></li>
                    <li>Document's id: <b>${document.id}</b></li>
                    <li>Document's location: <a href="${document.location}">${document.location}</a></li>
                    <li>Document's tags:
                        <ul>
                        <#list document.tags?keys as key>
                            <li>${key} : ${document.tags[key]}</li>
                        </#list>
                        </ul>
                    </li>
                    <li>Document's cached metadata:
                        <ul>
                        <#list document.cachedMetadata?keys as key>
                            <li>${key} : ${document.cachedMetadata[key]}</li>
                        </#list>
                        </ul>
                    </li>
                </ul>
            </li>
            <br>
        </#list>
        </ol>
    </body>
</html>