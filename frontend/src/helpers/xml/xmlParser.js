export function parseXmlComment(xmlString) {
  const parser = new DOMParser()
  const xmlDoc = parser.parseFromString(xmlString, 'application/xml')
  const sections = {}

  xmlDoc.documentElement.childNodes.forEach(section => {
    if (section.nodeType === 1) {
      const sectionName = section.nodeName
      sections[sectionName] = {}

      section.childNodes.forEach(field => {
        if (field.nodeType === 1) {
          sections[sectionName][field.nodeName] = field.textContent
        }
      })
    }
  })

  return sections
}


export function parseTranslationsXML(xmlString) {
  if (!xmlString) return {}

  const parser = new DOMParser()
  const xmlDoc = parser.parseFromString(xmlString, 'application/xml')
  const translations = {}

  xmlDoc.querySelectorAll('translation').forEach(node => {
    const lang = node.getAttribute('lang')
    const text = node.textContent.trim()
    if (lang && text) {
      translations[lang] = text
    }
  })

  return translations
}
