/*
 * [The "BSD licence"]
 * Copyright (c) 2013 Dandelion
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Dandelion nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.dandelion.datatables.thymeleaf.dialect;

import java.lang.reflect.InvocationTargetException;

import org.thymeleaf.processor.AttributeNameProcessorMatcher;
import org.thymeleaf.processor.IAttributeNameProcessorMatcher;

import com.github.dandelion.datatables.core.exception.DataTableProcessingException;
import com.github.dandelion.datatables.thymeleaf.processor.AbstractDatatablesAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.ajax.TablePipeSizeAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.ajax.TablePipeliningAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.ajax.TableProcessingAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.ajax.TableServerDataAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.ajax.TableServerMethodAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.ajax.TableServerParamsAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.ajax.TableServerSideAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.ajax.TableUrlAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableAppearAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableAutoWidthAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableCdnAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableConfGroupAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableDisplayLengthAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableDomAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableExportAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableExportLinksAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableFilterAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableFilterPlaceholderAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableInfoAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableLabelsAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableLengthChangeAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableLengthMenuAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TablePaginateAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TablePaginationTypeAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableScrollCollapseAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableScrollXInnerAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableScrollXAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableScrollYAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableSortAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.basic.TableStripeClassesAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TableCustomExtensionsProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyCallbackCookieProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyCallbackCreatedRowProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyCallbackDrawProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyCallbackFooterProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyCallbackFormatNumberProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyCallbackHeaderProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyCallbackInfoProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyCallbackInitProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyCallbackPreDrawProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyCallbackRowProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyExportAutoSizeAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyExportFilenameAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyExportHeaderAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyExportLinkClassAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyExportLinkLabelAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyExportLinkStyleAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.TbodyExportLinkUrlAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.feature.ThExportFilenameAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.plugin.TheadColReorderAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.plugin.TheadFixedHeaderAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.plugin.TheadScrollerAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.theme.TableThemeAttrProcessor;
import com.github.dandelion.datatables.thymeleaf.processor.attr.theme.TableThemeOptionAttrProcessor;

/**
 * All attribute processors used by Dandelion-DataTables.
 *
 * @since 0.8.9
 */
public enum TableAttrProcessors {
	
	// Configuration
	CONF_GROUP(TableConfGroupAttrProcessor.class, "confGroup", "table"),
	
    // Basic processors
    BASIC_AUTO_WIDTH(TableAutoWidthAttrProcessor.class, "autowidth", "table"),
    BASIC_CDN(TableCdnAttrProcessor.class, "cdn", "table"),
    BASIC_FILTER(TableFilterAttrProcessor.class, "filter", "table"),
    BASIC_INFO(TableInfoAttrProcessor.class, "info", "table"),
    BASIC_PAGINATE(TablePaginateAttrProcessor.class, "paginate", "table"),
    BASIC_SORT(TableSortAttrProcessor.class, "sort", "table"),
    BASIC_APPEAR(TableAppearAttrProcessor.class, "appear", "table"),
    BASIC_FILTER_PLACEHOLDER(TableFilterPlaceholderAttrProcessor.class, "filterplaceholder", "table"),
    BASIC_LABELS(TableLabelsAttrProcessor.class, "labels", "table"),
    BASIC_PAGINATION_TYPE(TablePaginationTypeAttrProcessor.class, "paginationtype", "table"),
    BASIC_LENGTH_MENU(TableLengthMenuAttrProcessor.class, "lengthmenu", "table"),
    BASIC_STRIP_CLASSES(TableStripeClassesAttrProcessor.class, "stripeclasses", "table"),
    BASIC_DISPLAY_LENGTH(TableDisplayLengthAttrProcessor.class, "displaylength", "table"),
    BASIC_LENGTH_CHANGE(TableLengthChangeAttrProcessor.class, "lengthchange", "table"),
    BASIC_SCROLL_Y(TableScrollYAttrProcessor.class, "scrolly", "table"),
    BASIC_SCROLL_COLLAPSE(TableScrollCollapseAttrProcessor.class, "scrollcollapse", "table"),
    BASIC_SCROLL_X(TableScrollXAttrProcessor.class, "scrollx", "table"),
    BASIC_SCROLL_INNER(TableScrollXInnerAttrProcessor.class, "scrollinner", "table"),
    BASIC_DOM(TableDomAttrProcessor.class, "dom", "table"),
    
    // Plugin processors
    PLUGIN_SCROLLER(TheadScrollerAttrProcessor.class, "scroller", "thead"),
    PLUGIN_COLUMN_RECORDER(TheadColReorderAttrProcessor.class, "colreorder", "thead"),
    PLUGIN_FIXED_HEADER(TheadFixedHeaderAttrProcessor.class, "fixedheader", "thead"),

    // Feature processors
    FEATURE_CUSTOM_EXTENSIONS(TableCustomExtensionsProcessor.class, "ext", "table"),
    FEATURE_EXPORT(TableExportAttrProcessor.class, "export", "table"),
    FEATURE_EXPORT_LINKS(TableExportLinksAttrProcessor.class, "exportLinks", "table"),
    FEATURE_EXPORT_FILENAME(ThExportFilenameAttrProcessor.class, "filename", "th"),

    EXPORT_HEADER_CSV(TbodyExportHeaderAttrProcessor.class, "csv:header", "tbody"),
    EXPORT_HEADER_PDF(TbodyExportHeaderAttrProcessor.class, "pdf:header", "tbody"),
    EXPORT_HEADER_XLS(TbodyExportHeaderAttrProcessor.class, "xls:header", "tbody"),
    EXPORT_HEADER_XLSX(TbodyExportHeaderAttrProcessor.class, "xlsx:header", "tbody"),
    EXPORT_HEADER_XML(TbodyExportHeaderAttrProcessor.class, "xml:header", "tbody"),

    EXPORT_AUTOSIZE_XLS(TbodyExportAutoSizeAttrProcessor.class, "xls:autosize", "tbody"),
    EXPORT_AUTOSIZE_XLSX(TbodyExportAutoSizeAttrProcessor.class, "xlsx:autosize", "tbody"),

    EXPORT_LINK_CLASS_CSV(TbodyExportLinkClassAttrProcessor.class, "csv:class", "tbody"),
    EXPORT_LINK_CLASS_PDF(TbodyExportLinkClassAttrProcessor.class, "pdf:class", "tbody"),
    EXPORT_LINK_CLASS_XLS(TbodyExportLinkClassAttrProcessor.class, "xls:class", "tbody"),
    EXPORT_LINK_CLASS_XLSX(TbodyExportLinkClassAttrProcessor.class, "xlsx:class", "tbody"),
    EXPORT_LINK_CLASS_XML(TbodyExportLinkClassAttrProcessor.class, "xml:class", "tbody"),

    EXPORT_LINK_STYLE_CSV(TbodyExportLinkStyleAttrProcessor.class, "csv:style", "tbody"),
    EXPORT_LINK_STYLE_PDF(TbodyExportLinkStyleAttrProcessor.class, "pdf:style", "tbody"),
    EXPORT_LINK_STYLE_XLS(TbodyExportLinkStyleAttrProcessor.class, "xls:style", "tbody"),
    EXPORT_LINK_STYLE_XLSX(TbodyExportLinkStyleAttrProcessor.class, "xlsx:style", "tbody"),
    EXPORT_LINK_STYLE_XML(TbodyExportLinkStyleAttrProcessor.class, "xml:style", "tbody"),

    EXPORT_LINK_LABEL_CSV(TbodyExportLinkLabelAttrProcessor.class, "csv:label", "tbody"),
    EXPORT_LINK_LABEL_PDF(TbodyExportLinkLabelAttrProcessor.class, "pdf:label", "tbody"),
    EXPORT_LINK_LABEL_XLS(TbodyExportLinkLabelAttrProcessor.class, "xls:label", "tbody"),
    EXPORT_LINK_LABEL_XLSX(TbodyExportLinkLabelAttrProcessor.class, "xlsx:label", "tbody"),
    EXPORT_LINK_LABEL_XML(TbodyExportLinkLabelAttrProcessor.class, "xml:label", "tbody"),

    EXPORT_LINK_URL_CSV(TbodyExportLinkUrlAttrProcessor.class, "csv:url", "tbody"),
    EXPORT_LINK_URL_PDF(TbodyExportLinkUrlAttrProcessor.class, "pdf:url", "tbody"),
    EXPORT_LINK_URL_XLS(TbodyExportLinkUrlAttrProcessor.class, "xls:url", "tbody"),
    EXPORT_LINK_URL_XLSX(TbodyExportLinkUrlAttrProcessor.class, "xlsx:url", "tbody"),
    EXPORT_LINK_URL_XML(TbodyExportLinkUrlAttrProcessor.class, "xml:url", "tbody"),
    
    EXPORT_FILENAME_CSV(TbodyExportFilenameAttrProcessor.class, "csv:filename", "tbody"),
    EXPORT_FILENAME_PDF(TbodyExportFilenameAttrProcessor.class, "pdf:filename", "tbody"),
    EXPORT_FILENAME_XLS(TbodyExportFilenameAttrProcessor.class, "xls:filename", "tbody"),
    EXPORT_FILENAME_XLSX(TbodyExportFilenameAttrProcessor.class, "xlsx:filename", "tbody"),
    EXPORT_FILENAME_XML(TbodyExportFilenameAttrProcessor.class, "xml:filename", "tbody"),

    // AJAX processors
    AJAX_URL(TableUrlAttrProcessor.class, "url", "table"),
    AJAX_SERVER_SIDE(TableServerSideAttrProcessor.class, "serverside", "table"),
    AJAX_PIPELINING(TablePipeliningAttrProcessor.class, "pipelining", "table"),
    AJAX_PIPE_SI(TablePipeSizeAttrProcessor.class, "pipesize", "table"),
    AJAX_PROCESSING(TableProcessingAttrProcessor.class, "processing", "table"),
    AJAX_SERVER_DATA(TableServerDataAttrProcessor.class, "serverdata", "table"),
    AJAX_SERVER_PARAMS(TableServerParamsAttrProcessor.class, "serverparams", "table"),
    AJAX_SERVER_METHOD(TableServerMethodAttrProcessor.class, "servermethod", "table"),

    // Theme processors
    THEME(TableThemeAttrProcessor.class, "theme", "table"),
    THEME_OPTION(TableThemeOptionAttrProcessor.class, "themeOption", "table"),

    // Callbacks processors
    CALLBACK_COOKIE(TbodyCallbackCookieProcessor.class, "cbk:cookie", "tbody"),
    CALLBACK_CREATE_ROW(TbodyCallbackCreatedRowProcessor.class, "cbk:createdrow", "tbody"),
    CALLBACK_DRAW(TbodyCallbackDrawProcessor.class, "cbk:draw", "tbody"),
    CALLBACK_FOOTER(TbodyCallbackFooterProcessor.class, "cbk:footer", "tbody"),
    CALLBACK_FORMAT_NUMBER(TbodyCallbackFormatNumberProcessor.class, "cbk:format", "tbody"),
    CALLBACK_HEADER(TbodyCallbackHeaderProcessor.class, "cbk:header", "tbody"),
    CALLBACK_INFO(TbodyCallbackInfoProcessor.class, "cbk:info", "tbody"),
    CALLBACK_INIT(TbodyCallbackInitProcessor.class, "cbk:init", "tbody"),
    CALLBACK_PRE_DRAW(TbodyCallbackPreDrawProcessor.class, "cbk:predraw", "tbody"),
    CALLBACK_ROW(TbodyCallbackRowProcessor.class, "cbk:row", "tbody");

    private Class<? extends AbstractDatatablesAttrProcessor> processorClass;
    private String attributeName;
    private String elementNameFilter;

    private TableAttrProcessors(Class<? extends AbstractDatatablesAttrProcessor> processorClass, String attributeName, String elementNameFilter) {
        this.processorClass = processorClass;
        this.attributeName = attributeName;
        this.elementNameFilter = elementNameFilter;
    }

    public AbstractDatatablesAttrProcessor getProcessor() {
        AttributeNameProcessorMatcher matcher = new AttributeNameProcessorMatcher(attributeName, elementNameFilter);
        try {
            return processorClass.getDeclaredConstructor(IAttributeNameProcessorMatcher.class).newInstance(matcher);
        } catch (InstantiationException e) {
        	throw new DataTableProcessingException(e);
        } catch (IllegalAccessException e) {
        	throw new DataTableProcessingException(e);
        } catch (InvocationTargetException e) {
        	throw new DataTableProcessingException(e);
        } catch (NoSuchMethodException e) {
        	throw new DataTableProcessingException(e);
        }
    }
}