package com.fasterxml.transistore.basic;

import com.fasterxml.clustermate.api.PathType;
import com.fasterxml.clustermate.api.DecodableRequestPath;
import com.fasterxml.clustermate.api.RequestPathBuilder;
import com.fasterxml.clustermate.api.RequestPathStrategy;

/**
 * {@link RequestPathStrategy} implementation for
 * Basic TransiStore configuration.
 *<p>
 * Paths are mapped as follows:
 *<ul>
 * <li>Store entries under ".../store/":
 *  <ul>
 *    <li>".../store/entry" for single-entry</li>
 *    <li>".../store/list" for range access</li>
 *    <li>".../store/status" for store status (admin interface)</li>
 *    <li>".../store/findEntry" for redirecting single-entry</li>
 *    <li>".../store/findList" for redirecting range access</li>
 *  </ul>
 * <li>Node status entries under ".../node/":
 *  <ul>
 *    <li>".../node/status" for basic status</li>
 *  </ul>
 *  <ul>
 *    <li>".../node/metrics" for various metrics</li>
 *  </ul>
 *</ul>
 * <li>Sync entries under ".../sync/":
 *  <ul>
 *    <li>".../sync/list" for accessing metadata for changes</li>
 *    <li>".../sync/pull" for pulling entries to sync</li>
 *  </ul>
 *</ul>
 */
@SuppressWarnings("unchecked")
public class BasicTSPaths extends RequestPathStrategy
{
    protected final static String FIRST_SEGMENT_STORE = "store";
    protected final static String FIRST_SEGMENT_NODE = "node";
    protected final static String FIRST_SEGMENT_SYNC = "sync";

    protected final static String SECOND_SEGMENT_STORE_ENTRY = "entry";
    protected final static String SECOND_SEGMENT_STORE_LIST = "list";
    protected final static String SECOND_SEGMENT_STORE_STATUS = "status";
    protected final static String SECOND_SEGMENT_STORE_FIND_ENTRY = "findEntry";
    protected final static String SECOND_SEGMENT_STORE_FIND_LIST = "findList";

    protected final static String SECOND_SEGMENT_NODE_STATUS = "status";
    protected final static String SECOND_SEGMENT_NODE_METRICS = "metrics";

    protected final static String SECOND_SEGMENT_SYNC_LIST = "list";
    protected final static String SECOND_SEGMENT_SYNC_PULL = "pull";
    
    /*
    /**********************************************************************
    /* Path building
    /**********************************************************************
     */

    @Override
    public <B extends RequestPathBuilder> B appendPath(B basePath,
            PathType type)
    {
        switch (type) {
        case NODE_METRICS:
            return (B) _nodePath(basePath).addPathSegment(SECOND_SEGMENT_NODE_METRICS);
        case NODE_STATUS:
            return (B) _nodePath(basePath).addPathSegment(SECOND_SEGMENT_NODE_STATUS);

        case STORE_ENTRY:
            return (B) _storePath(basePath).addPathSegment(SECOND_SEGMENT_STORE_ENTRY);
        case STORE_FIND_ENTRY:
            return (B) _storePath(basePath).addPathSegment(SECOND_SEGMENT_STORE_FIND_ENTRY);
        case STORE_FIND_LIST:
            return (B) _storePath(basePath).addPathSegment(SECOND_SEGMENT_STORE_FIND_LIST);
        case STORE_LIST:
            return (B) _storePath(basePath).addPathSegment(SECOND_SEGMENT_STORE_LIST);
        case STORE_STATUS:
            return (B) _storePath(basePath).addPathSegment(SECOND_SEGMENT_STORE_STATUS);

        case SYNC_LIST:
            return (B) _syncPath(basePath).addPathSegment(SECOND_SEGMENT_SYNC_LIST);
        case SYNC_PULL:
            return (B) _syncPath(basePath).addPathSegment(SECOND_SEGMENT_SYNC_PULL);
        }
        throw new IllegalStateException();
    }

    /*
    /**********************************************************************
    /* Path matching (decoding)
    /**********************************************************************
     */

    @Override
    public PathType matchPath(DecodableRequestPath pathDecoder)
    {
        String full = pathDecoder.getPath();
        if (pathDecoder.matchPathSegment(FIRST_SEGMENT_STORE)) {
            if (pathDecoder.matchPathSegment(SECOND_SEGMENT_STORE_ENTRY)) {
                return PathType.STORE_ENTRY;
            }
            if (pathDecoder.matchPathSegment(SECOND_SEGMENT_STORE_LIST)) {
                return PathType.STORE_LIST;
            }
        } else if (pathDecoder.matchPathSegment(FIRST_SEGMENT_NODE)) {
            if (pathDecoder.matchPathSegment(SECOND_SEGMENT_NODE_STATUS)) {
                return PathType.NODE_STATUS;
            }
            if (pathDecoder.matchPathSegment(SECOND_SEGMENT_NODE_METRICS)) {
                return PathType.NODE_METRICS;
            }
        } else if (pathDecoder.matchPathSegment(FIRST_SEGMENT_SYNC)) {
            if (pathDecoder.matchPathSegment(SECOND_SEGMENT_SYNC_LIST)) {
                return PathType.SYNC_LIST;
            }
            if (pathDecoder.matchPathSegment(SECOND_SEGMENT_SYNC_PULL)) {
                return PathType.SYNC_PULL;
            }
        }
        // if no match, need to reset
        pathDecoder.setPath(full);
        return null;
    }
    
    /*
    /**********************************************************************
    /* Internal methods
    /**********************************************************************
     */
    
    protected <K extends RequestPathBuilder> K _storePath(K nodeRoot) {
        return (K) nodeRoot.addPathSegment(FIRST_SEGMENT_STORE);
    }

    protected <K extends RequestPathBuilder> K _nodePath(K nodeRoot) {
        return (K) nodeRoot.addPathSegment(FIRST_SEGMENT_NODE);
    }

    protected <K extends RequestPathBuilder> K _syncPath(K nodeRoot) {
        return (K) nodeRoot.addPathSegment(FIRST_SEGMENT_SYNC);
    }
}
