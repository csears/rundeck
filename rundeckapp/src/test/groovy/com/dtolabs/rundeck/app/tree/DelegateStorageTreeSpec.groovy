package com.dtolabs.rundeck.app.tree

import com.dtolabs.rundeck.core.storage.StorageTree
import spock.lang.Specification

class DelegateStorageTreeSpec extends Specification {

        def "updateTreeConfig no storage updates"(){

            given:
                StorageTreeCreator creator = Mock(StorageTreeCreator){
                    getStorageConfigMap() >> ["config1": "config1Def", "config2": "config2Def"]
                }
                DelegateStorageTree tree = new DelegateStorageTree()
                tree.configuration = ["config1": "config1Def", "config2": "config2Def"]
                tree.creator=creator

            when:
                tree.updateTreeConfig(null)

            then:
                tree.delegate == null
        }

    def "updateTreeConfig w storage updates"(){

        given:
        StorageTreeCreator creator = Mock(StorageTreeCreator){
            getStorageConfigMap() >> ["config1": "config1Def", "config2": "config2Def"]
            create() >> Mock(StorageTree)
        }
        DelegateStorageTree tree = new DelegateStorageTree()
        tree.configuration = ["config1": "config1Def", "config3": "config3Def"]
        tree.creator=creator

        when:
        tree.updateTreeConfig(null)

        then:
        tree.delegate != null
    }

}
