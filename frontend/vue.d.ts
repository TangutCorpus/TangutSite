/// <reference types="vue" />

import {I18n} from 'vue-i18n'

declare module '@vue/runtime-core' {
    interface ComponentCustomProperties {
        $t: I18n['global']['t']
        $i18n: I18n['global']
    }
}

declare module '*.vue' {
    import {DefineComponent} from 'vue'
    const component: DefineComponent<{}, {}, any>
    export default component
}